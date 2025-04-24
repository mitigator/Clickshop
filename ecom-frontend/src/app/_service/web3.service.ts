// web3.service.ts
import { Injectable } from '@angular/core';
import Web3 from 'web3';
import detectEthereumProvider from '@metamask/detect-provider';

@Injectable({
  providedIn: 'root'
})
export class Web3Service {
  private web3: any;
  private accounts: string[] = [];

  constructor() { }

  async connectWallet(): Promise<string[]> {
    const provider = await detectEthereumProvider();
    
    if (provider) {
      this.web3 = new Web3(provider as any);
      try {
        this.accounts = await this.web3.eth.requestAccounts();
        return this.accounts;
      } catch (error) {
        console.error('User denied account access');
        throw error;
      }
    } else {
      throw new Error('Please install MetaMask!');
    }
  }

  async getBalance(): Promise<string> {
    if (!this.accounts.length) {
      await this.connectWallet();
    }
    const balance = await this.web3.eth.getBalance(this.accounts[0]);
    return this.web3.utils.fromWei(balance, 'ether');
  }

  async disconnect() {
    this.accounts = [];
    // Note: Actual wallet disconnection must be done through MetaMask UI
  }

  async sendPayment(toAddress: string, amount: number): Promise<any> {
    if (!this.accounts.length) {
      await this.connectWallet();
    }
    
    const amountInWei = this.web3.utils.toWei(amount.toString(), 'ether');
    
    return this.web3.eth.sendTransaction({
      from: this.accounts[0],
      to: toAddress,
      value: amountInWei
    });
  }
}