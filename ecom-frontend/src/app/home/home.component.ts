import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { map, finalize } from 'rxjs/operators';
import { ImageProcessingService } from '../image-processing.service';
import { Product } from '../_model/product.model';
import { ProductService } from '../_services/product.service';
import { ThemeService } from '../_services/theme.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  pageNumber: number = 0;
  productDetails = [];
  showLoadButton = false;
  isLoading = false;
  lastSearchTerm = '';
  isDarkTheme = false;

  constructor(
    private productService: ProductService,
    private imageProcessingService: ImageProcessingService,
    private router: Router,
    private themeService: ThemeService
  ) { }

  ngOnInit(): void {
    this.isLoading = true;
    this.getAllProducts();
    
    // Subscribe to theme changes
    this.themeService.isDarkTheme.subscribe(isDark => {
      this.isDarkTheme = isDark;
    });
  }

  searchByKeyword(searchKeyword) {
    if (this.lastSearchTerm !== searchKeyword) {
      this.lastSearchTerm = searchKeyword;
      this.pageNumber = 0;
      this.productDetails = [];
      this.isLoading = true;
      this.getAllProducts(searchKeyword);
    }
  }

  resetSearch() {
    this.lastSearchTerm = '';
    this.pageNumber = 0;
    this.productDetails = [];
    this.isLoading = true;
    this.getAllProducts();
  }

  public getAllProducts(searchKey: string = "") {
    this.isLoading = true;
    this.productService.getAllProducts(this.pageNumber, searchKey)
      .pipe(
        map((x: Product[], i) => x.map((product: Product) => this.imageProcessingService.createImages(product))),
        finalize(() => {
          this.isLoading = false;
        })
      )
      .subscribe(
        (resp: Product[]) => {
          if (resp.length == 8) {
            this.showLoadButton = true;
          } else {
            this.showLoadButton = false;
          }
          resp.forEach(p => this.productDetails.push(p));
        },
        (error: HttpErrorResponse) => {
          console.log(error);
          this.isLoading = false;
        }
      );
  }

  public loadMoreProduct() {
    this.pageNumber = this.pageNumber + 1;
    this.getAllProducts(this.lastSearchTerm);
  }

  showProductDetails(productId) {
    this.router.navigate(['/productViewDetails', { productId: productId }]);
  }

  calculateDiscount(actualPrice: number, discountedPrice: number): number {
    if (actualPrice <= 0 || discountedPrice >= actualPrice) return 0;
    const discount = ((actualPrice - discountedPrice) / actualPrice) * 100;
    return Math.round(discount);
  }
}