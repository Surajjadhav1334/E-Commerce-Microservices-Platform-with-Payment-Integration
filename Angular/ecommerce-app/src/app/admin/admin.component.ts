import { Component, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service'; // 👈 1. Path check karun import kara
import { Router } from '@angular/router'; // 👈 2. Router import kara
import { ProductService } from '../services/product.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit{

  products: any[]=[];

  newProduct = {
    name : '',
    price : 0,
    quantity: 0
  }

  selectedProduct: any = null;
  view: string = 'list';
  
  // 3. Constructor madhe ya donhi goshti Inject karne compulsory aahe
  constructor(
    private authService: AuthService,
    private router: Router,
    private productService: ProductService
  ) {}

  ngOnInit()  {
      this.loadProducts();
  }

  loadProducts()
  {
    this.productService.getProducts().subscribe({
      next: (data: any) => {
        this.products = data;
      },
      error: (err) => {
         console.log("Error fetching products", err);
      }
    });
  }

  addProduct()
  {
    this.productService.addProduct(this.newProduct).subscribe({
      next: () => {
        alert("Product Added");
        this.newProduct = {name: '', price: 0, quantity: 0};
        this.loadProducts();
      },
      error: (err)=> {
        console.log("add error", err);
      }
    });
  }

    deleteProduct(id: number) {
    this.productService.deleteProduct(id).subscribe({
      next: () => {
        alert("Deleted ❌");
        this.loadProducts();
      },
      error: (err) => {
        console.log("Delete Error", err);
      }
    });
  }

  //  getProductbyid(id: number) {
  //   this.productService.getProductbyId(id).subscribe({
  //     next: () => {
  //       alert("");
  //       this.loadProducts();
  //     },
  //     error: (err) => {
  //       console.log("Delete Error", err);
  //     }
  //   });
  // }

   editProduct(id: number) {
    this.productService.getProductbyId(id).subscribe({
      next: (data) => {
        this.selectedProduct = data;
      },
       error: (err) => {
        console.log("Error fetching product", err);
      }
    });
  }

    updateProduct() {
    this.productService.updateProduct(this.selectedProduct.id, this.selectedProduct)
      .subscribe({
        next: () => {
          alert("Updated ✅");
          this.selectedProduct = null;
          this.loadProducts();
        },
        error: (err) => {
          console.log("Update Error", err);
        }
      });
  }


  logout() {
    this.authService.logout(); // ✅ Ata error yenar nahi
    this.router.navigate(['/login']); // ✅ Redirect vyavasthit hoil
  }


}
