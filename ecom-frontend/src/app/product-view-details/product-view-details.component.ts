import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from '../_model/product.model';
import { ProductService } from '../_services/product.service';


@Component({
  selector: 'app-product-view-details',
  templateUrl: './product-view-details.component.html',
  styleUrls: ['./product-view-details.component.css']
})
export class ProductViewDetailsComponent implements OnInit {

  selectProductIndex = 0;
  product: Product;

  constructor(
    private activatedRoute: ActivatedRoute, 
    private router: Router,
    private productService: ProductService
  ) { }

  ngOnInit(): void {
    this.product = this.activatedRoute.snapshot.data['product'];
  }

  changeIndex(index: number) {
    this.selectProductIndex = index;
  }

  navigateImage(direction: number) {
    this.selectProductIndex += direction;
  }

  buyProduct(productId: number) {
    this.router.navigate(['/buyProduct', {
      isSingleProductCheckout: true, id: productId
    }]);
  }

  addToCart(productId: number) {
    this.productService.addToCart(productId).subscribe(
      (response) => {
        console.log(response);
      },
      (error) => {
        console.log(error);
      }
    );
  }

  calculateDiscount(actualPrice: number, discountedPrice: number): number {
    return Math.round(((actualPrice - discountedPrice) / actualPrice) * 100);
  }

  zoomImage() {
    // Implement zoom functionality if needed
  }
}