import { Product } from "./product.model";

export interface MyOrderDetails {
    orderId: number;
    orderFullName: string;
    orderFullOrder: string; // Note: This seems like a typo - should it be orderFullAddress?
    orderContactNumber: string;
    orderAlternateContactNumber: string;
    orderStatus: string;
    orderAmount: number;
    product: Product;
    user: any;
    paymentMethod?: string;  // Optional field for payment method
    transactionHash?: string; // Optional field for transaction hash
}