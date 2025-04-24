import { Component, OnInit } from '@angular/core';
import { ProductService } from '../_services/product.service';

@Component({
  selector: 'app-order-detais',
  templateUrl: './order-detais.component.html',
  styleUrls: ['./order-detais.component.css']
})
export class OrderDetaisComponent implements OnInit {

  displayedColumns: string[] = ['Id', 'Product Name', 'Name', 'Address', 'Contact No', 'Status'];
  displayedColumnsWithActions: string[] = [...this.displayedColumns, 'Actions'];
  dataSource = [];
  
  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.getAllOrderDetailsForAdmin();
  }

  getAllOrderDetailsForAdmin() {
    this.productService.getAllOrderDetailsForAdmin().subscribe(
      (resp) => {
        console.log(resp);
        this.dataSource = resp;
      }, (error) => {
        console.log(error);
      }
    );
  }

  getStatusClass(status: string): string {
    const statusLower = status.toLowerCase();
    if (statusLower.includes('pending')) return 'pending';
    if (statusLower.includes('complete')) return 'completed';
    if (statusLower.includes('cancel')) return 'cancelled';
    if (statusLower.includes('ship')) return 'shipped';
    return '';
  }
}