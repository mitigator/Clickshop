// payment-dialog.component.ts
import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Web3Service } from '../_service/web3.service';
@Component({
  selector: 'app-payment-dialog',
  templateUrl: './payment-dialog.component.html',
  styleUrls: ['./payment-dialog.component.css']
})
export class PaymentDialogComponent {
  paymentInProgress = false;
  paymentSuccess = false;
  paymentError = false;
  errorMessage = '';

  constructor(
    public dialogRef: MatDialogRef<PaymentDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private web3Service: Web3Service
  ) {}

  async processPayment() {
    this.paymentInProgress = true;
    this.paymentError = false;
    
    try {
      const businessWalletAddress = '0x04677d878669bDE0a60e49fE00aD4c284dab1992';
      
      await this.web3Service.sendPayment(businessWalletAddress, this.data.amount);
      
      this.paymentSuccess = true;
      setTimeout(() => {
        this.dialogRef.close({ success: true });
      }, 2000);
    } catch (error) {
      this.paymentError = true;
      this.errorMessage = error.message;
      this.paymentInProgress = false;
    }
  }
}