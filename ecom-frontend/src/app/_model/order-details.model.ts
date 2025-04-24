// order-details.model.ts
import { OrderQuantity } from "./order-quantity.model";

export interface OrderDetails {
    fullName: string;
    fullAddress: string;
    contactNumber: string;
    alternateContactNumber: string;
    orderProductQuantityList: OrderQuantity[];
    paymentMethod?: string;  // Optional field for payment method
    transactionHash?: string; // Optional field for transaction hash
}