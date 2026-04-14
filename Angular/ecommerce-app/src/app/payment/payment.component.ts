import { Component } from '@angular/core';
import { PaymentService } from '../services/payment.service';

declare var Razorpay: any;

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
})
export class PaymentComponent {

  constructor(private paymentService: PaymentService) {}

  pay() {
    const request = {
      orderId: 20,
      amount: 500
    };

    // 1️⃣ Create Payment Order
    this.paymentService.createPayment(request).subscribe((res: any) => {

      const options = {
        key: 'rzp_test_SXrZL65QmT4jPZ',
        amount: res.amount * 100,   // ⚠️ adjust based on backend
        currency: 'INR',
        name: 'Test App',
        description: 'Payment Test',
        order_id: res.razorpayOrderId,

        handler: (response: any) => {
          console.log("Payment Success:", response);

          // 2️⃣ VERIFY PAYMENT
          this.paymentService.verifyPayment(
            response.razorpay_order_id,
            response.razorpay_payment_id
          ).subscribe({
            next: (res) => {
              console.log("Verified:", res);
              alert("✅ Payment Verified Successfully");
            },
            error: (err) => {
              console.error("Verification Failed:", err);
              alert("❌ Payment Verification Failed");
            }
          });
        },

        modal: {
          ondismiss: () => {
            console.log("Payment Cancelled");
            alert("❌ Payment Cancelled");
          }
        }
      };

      const rzp = new Razorpay(options);
      rzp.open();
    });
  }
}
