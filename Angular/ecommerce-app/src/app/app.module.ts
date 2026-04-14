import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { PaymentComponent } from './payment/payment.component';
import { AppComponent } from './app.component';
import { BrowserModule } from '@angular/platform-browser';
import { LoginComponent } from './auth/login/login.component';
import { OrderListComponent } from './order/order-list/order-list.component';
import { FormsModule } from '@angular/forms';
import { RegisterComponent } from './auth/register/register.component';
import { HTTP_INTERCEPTORS } from '@angular/common/http'; // 👈 He import kara
import { AuthInterceptor } from './auth.interceptor';
import { AppRoutingModule } from './app-routing.module';
import { AdminComponent } from './admin/admin.component';
import { UserComponent } from './user/user.component'; // 👈 Tumcha interceptor path check kara

@NgModule({

  imports: [
    HttpClientModule,
    BrowserModule,
    FormsModule,
    AppRoutingModule,
  ],

  declarations: [
    AppComponent,
     PaymentComponent,
     LoginComponent,
     OrderListComponent,
    LoginComponent,
    AdminComponent,
    UserComponent,
  ],
   providers: [{ provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }],
  bootstrap: [AppComponent]
}
)
export class AppModule { }
