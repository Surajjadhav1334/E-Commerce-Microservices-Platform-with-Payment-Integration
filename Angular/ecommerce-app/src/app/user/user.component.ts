import { Component, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';
import { OrderService } from '../services/order.service';
import { ProductService } from '../services/product.service';
declare var Razorpay: any;

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css'],
})
export class UserComponent implements OnInit {
  products: any[] = [];
  orders: any[] = [];

  order = {
    productId: 0,
    quantity: 1,
    amount: 0,
  };

  selectedOrder: any = null;
  view: string = 'products';

  constructor(
    private authService: AuthService,
    private orderService: OrderService,
    private productService: ProductService,
    private router: Router
  ) {}

  setView(v: string) {
    this.view = v;
  }

  logout() {
    this.authService.logout(); // ✅ Ata error yenar nahi
    this.router.navigate(['/login']); // ✅ Redirect vyavasthit hoil
  }

  ngOnInit() {
    this.loadProducts();
    this.loadOrders();
    this.view = 'orders';
  }

  loadProducts() {
    this.productService.getProducts().subscribe({
      next: (res: any) => (this.products = res),
      error: (err) => console.log(err),
    });
  }

  loadOrders() {
    this.orderService.getOrders().subscribe({
      next: (data: any) => {
        console.log('Orders API Response 👉', data);
        this.orders = Array.isArray(data) ? data : data.data;
        this.orders = [...this.orders];
      },
      error: (err) => {
        console.log('Error fetching orders', err);
      },
    });
  }

  placeOrder(p: any) {
    this.order.productId = p.id;
    this.order.quantity = 1;

    // 👇 IMPORTANT (Payment error fix)
    this.order.amount = p.price * this.order.quantity;

    console.log('Sending Order:', this.order); // debug

    this.orderService.addOrders(this.order).subscribe({
      next: () => {
        alert('Order Placed ✅');
        this.loadOrders();
        this.view = 'orders';
      },
      error: (err) => {
        console.log('Order Error', err);
      },
    });
  }

  pay(order: any) {
    console.log('Order for payment:', order);

    const paymentData = {
      orderId: order.id,
      amount: order.price * order.quantity,
    };

    this.orderService.createPayment(paymentData).subscribe((res: any) => {
      const options = {
        key: 'rzp_test_SXrZL65QmT4jPZ', // 👈 replace
        amount: res.amount * 100,
        currency: 'INR',
        name: 'My App',
        description: 'Order Payment',
        order_id: res.razorpayOrderId,

        handler: (response: any) => {
          this.orderService
            .verifyPayment({
              orderId: order.id,
              razorpayPaymentId: response.razorpay_payment_id,
            })
            .subscribe(() => {
              alert('Payment Success ✅');
              this.loadOrders();
            });
        },
      };

      const rzp = new Razorpay(options);
      rzp.open();
    });
  }

  // ✅ EDIT ORDER (fill form)
  editOrder(o: any) {
    console.log('Editing:', o);
    this.selectedOrder = { ...o };
    console.log('SelectedOrder:', this.selectedOrder);
  }

  // ✅ UPDATE ORDER
  updateOrder() {
    const updatedData = {
      productId: this.selectedOrder.productId,
      quantity: this.selectedOrder.quantity,
      price: this.selectedOrder.price,
    };

    this.orderService
      .updateOrder(this.selectedOrder.id, updatedData)
      .subscribe({
        next: () => {
          alert('Order Updated ✅');
          this.selectedOrder = null;
          this.loadOrders();
        },
        error: (err) => console.log('Update Error', err),
      });
  }

  // ✅ DELETE ORDER
  deleteOrder(id: number) {
    this.orderService.deleteOrder(id).subscribe({
      next: () => {
        alert('Order Deleted ❌');
        this.loadOrders();
      },
      error: (err) => console.log('Delete Error', err),
    });
  }
}
