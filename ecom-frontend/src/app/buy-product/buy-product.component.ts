import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { OrderDetails } from '../_model/order-details.model';
import { Product } from '../_model/product.model';
import { ProductService } from '../_services/product.service';
import Web3 from 'web3';
import { HttpClient } from '@angular/common/http';

declare let window: any;

@Component({
  selector: 'app-buy-product',
  templateUrl: './buy-product.component.html',
  styleUrls: ['./buy-product.component.css']
})
export class BuyProductComponent implements OnInit {
  isSingleProductCheckout: string = "";
  productDetails: Product[] = [];
  orderDetails: OrderDetails = {
    fullName: '',
    fullAddress: '',
    contactNumber: '',
    alternateContactNumber: '',
    orderProductQuantityList: [],
    paymentMethod: 'creditCard',
    transactionHash: null
  };
  
  // Payment properties
  paymentMethod: string = 'creditCard';
  isWalletConnected: boolean = false;
  walletAddress: string = '';
  walletBalance: string = '0';
  ethAmount: number = 0;
  ethRate: number = 0;
  isProcessing: boolean = false;
  
  private web3: any;

  constructor(
    private activatedRoute: ActivatedRoute,
    private productService: ProductService,
    private router: Router,
    private http: HttpClient
  ) { }

  ngOnInit(): void {
    this.productDetails = this.activatedRoute.snapshot.data['productDetails'];
    this.isSingleProductCheckout = this.activatedRoute.snapshot.paramMap.get("isSingleProductCheckout");
    
    this.productDetails.forEach(
      x => this.orderDetails.orderProductQuantityList.push(
        {productId: x.productId, quantity: 1}
      )
    );
    
    this.initWeb3();
    this.getEthExchangeRate();
  }

  async initWeb3() {
    if (window.ethereum) {
      try {
        this.web3 = new Web3(window.ethereum);
        // Check if already connected
        const accounts = await this.web3.eth.getAccounts();
        if (accounts.length > 0) {
          this.walletAddress = accounts[0];
          this.isWalletConnected = true;
          await this.updateWalletBalance();
        }
      } catch (error) {
        console.error('Error initializing Web3:', error);
        this.showAlert('Error initializing Web3: ' + error.message);
      }
    }
  }

  async connectWallet() {
    if (!this.web3) {
      this.showAlert('Please install MetaMask!');
      return;
    }

    try {
      this.isProcessing = true;
      const accounts = await window.ethereum.request({ method: 'eth_requestAccounts' });
      this.walletAddress = accounts[0];
      this.isWalletConnected = true;
      await this.updateWalletBalance();
      this.calculateEthAmount();
      this.showAlert('Wallet connected successfully!');
    } catch (error) {
      console.error('Error connecting wallet:', error);
      this.showAlert('Error connecting wallet: ' + error.message);
    } finally {
      this.isProcessing = false;
    }
  }

  async updateWalletBalance() {
    if (!this.walletAddress) return;
    
    const balance = await this.web3.eth.getBalance(this.walletAddress);
    this.walletBalance = this.web3.utils.fromWei(balance, 'ether');
  }

  getEthExchangeRate() {
    // In production, use a real API like CoinGecko or CoinMarketCap
    this.http.get('https://api.coingecko.com/api/v3/simple/price?ids=ethereum&vs_currencies=inr')
      .subscribe((data: any) => {
        this.ethRate = data.ethereum.inr;
        this.calculateEthAmount();
      }, error => {
        console.error('Error fetching ETH rate:', error);
        // Fallback rate if API fails
        this.ethRate = 200000; // Example fallback rate (1 ETH = 200,000 INR)
        this.calculateEthAmount();
      });
  }

  calculateEthAmount() {
    if (this.ethRate > 0) {
      // Calculate with full precision but store with limited decimals
      const calculatedAmount = this.getCalculatedGrandTotal() / this.ethRate;
      this.ethAmount = parseFloat(calculatedAmount.toFixed(8)); // Limit to 8 decimal places
    }
  }

  async placeOrder(orderForm: NgForm) {
    if (orderForm.invalid) {
      this.showAlert('Please fill all required fields');
      return;
    }

    this.isProcessing = true;
    this.orderDetails.paymentMethod = this.paymentMethod;

    try {
      if (this.paymentMethod === 'crypto') {
        await this.processCryptoPayment();
      }
      
      this.processRegularPayment(orderForm);
    } catch (error) {
      console.error('Order placement failed:', error);
      this.showAlert('Order placement failed: ' + error.message);
      this.isProcessing = false;
    }
  }

  shortenWalletAddress(address: string): string {
    if (!address) return '';
    return `${address.substring(0, 6)}...${address.substring(address.length - 4)}`;
  }

  async processCryptoPayment() {
    if (!this.isWalletConnected) {
      throw new Error('Please connect your wallet first');
    }
  
    // Round the ETH amount to 8 decimal places before conversion
    const roundedEthAmount = parseFloat(this.ethAmount.toFixed(8));
    
    // Verify sufficient balance
    const requiredWei = this.web3.utils.toWei(roundedEthAmount.toString(), 'ether');
    const balanceWei = await this.web3.eth.getBalance(this.walletAddress);
    
    if (Number(balanceWei) < Number(requiredWei)) {
      throw new Error('Insufficient balance in your wallet');
    }
  
    // Your business wallet address
    const businessAddress = '0xf39Fd6e51aad88F6F4ce6aB8827279cffFb92266';
    
    const tx = await this.web3.eth.sendTransaction({
      from: this.walletAddress,
      to: businessAddress,
      value: requiredWei,
      gas: 21000,
      gasPrice: await this.web3.eth.getGasPrice()
    });
  
    this.orderDetails.transactionHash = tx.transactionHash;
    this.showAlert('Payment successful! Transaction hash: ' + tx.transactionHash);
  }

  processRegularPayment(orderForm: NgForm) {
    this.productService.placeOrder(this.orderDetails, this.isSingleProductCheckout).subscribe(
      (resp) => {
        console.log(resp);
        orderForm.reset();
        this.router.navigate(["/orderConfirm"]);
      },
      (err) => {
        console.log(err);
        this.showAlert('Order placement failed');
        this.isProcessing = false;
      },
      () => {
        this.isProcessing = false;
      }
    );
  }

  // Existing methods
  getQuantityForProduct(productId: number): number {
    const filteredProduct = this.orderDetails.orderProductQuantityList.filter(
      (productQuantity) => productQuantity.productId === productId
    );
    return filteredProduct[0].quantity;
  }

  getCalculatedTotal(productId: number, productDiscountedPrice: number): number {
    const filteredProduct = this.orderDetails.orderProductQuantityList.filter(
      (productQuantity) => productQuantity.productId === productId
    );
    return filteredProduct[0].quantity * productDiscountedPrice;
  }

  onQuantityChanged(quantity: string, productId: number) {
    const qty = parseInt(quantity, 10);
    this.orderDetails.orderProductQuantityList.filter(
      (orderProduct) => orderProduct.productId === productId
    )[0].quantity = qty;
    this.calculateEthAmount();
  }

  getCalculatedGrandTotal(): number {
    let grandTotal = 0;
    this.orderDetails.orderProductQuantityList.forEach(
      (productQuantity) => {
        const product = this.productDetails.find(p => p.productId === productQuantity.productId);
        if (product) {
          grandTotal += product.productDiscountedPrice * productQuantity.quantity;
        }
      }
    );
    return grandTotal;
  }

  disconnectWallet() {
    this.isWalletConnected = false;
    this.walletAddress = '';
    this.walletBalance = '0';
    this.showAlert('Wallet disconnected successfully!');
  }

  private showAlert(message: string) {
    alert(message); // Simple browser alert as replacement for snackbar
  }
}