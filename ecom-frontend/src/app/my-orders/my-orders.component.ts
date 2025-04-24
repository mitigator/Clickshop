import { Component, OnInit } from '@angular/core';
import { MyOrderDetails } from '../_model/order.model';
import { ProductService } from '../_services/product.service';

@Component({
  selector: 'app-my-orders',
  templateUrl: './my-orders.component.html',
  styleUrls: ['./my-orders.component.css']
})
export class MyOrdersComponent implements OnInit {

  displayedColumns = ["Name", "Amount", "Status", "Receipt"];
  myOrderDetails: MyOrderDetails[] = [];
  selectedReceipt: MyOrderDetails | null = null;

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.getOrderDetails();
  }

  getOrderDetails() {
    this.productService.getMyOrders().subscribe(
      (resp: MyOrderDetails[]) => {
        this.myOrderDetails = resp;
      }, 
      (err) => {
        console.log(err);
      }
    );
  }

  getStatusClass(status: string): string {
    switch(status.toLowerCase()) {
      case 'placed':
        return 'status-placed';
      case 'shipped':
        return 'status-shipped';
      case 'delivered':
        return 'status-delivered';
      case 'cancelled':
        return 'status-cancelled';
      default:
        return 'status-default';
    }
  }

  generateReceipt(order: MyOrderDetails) {
    this.selectedReceipt = order;
  }

  printReceipt() {
    window.print();
  }
}