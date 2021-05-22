import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { ItemEditComponent } from '../item-edit/item-edit.component';
import { ItemService, UserService } from '../service';

interface ItemPage {
  content: Item[];
  totalElements: number;
  first: boolean;
  last: boolean;
  totalPages: number;
}

interface Item {
  id: string;
  name: string;
  dateEnd: string;
  price: string;
}

@Component({
  selector: 'app-item-list',
  templateUrl: './item-list.component.html',
  styleUrls: ['./item-list.component.css']
})
export class ItemListComponent implements OnInit {
  itemPage: ItemPage | any;
  public currentPage: number;

  filter = new FormControl('');

  constructor(
      private itemService: ItemService, 
      private userService: UserService, 
      public toastr: ToastrService,
      private modalService: NgbModal
    ) {
    this.currentPage = 0;
  }

  ngOnInit(): void {
    this.loadItems();
  }

  loadItems(): void {
    this.filter.setValue('');
    this.itemService.getItems().subscribe(data => {
      this.itemPage = data;        
    })
  }

  getNextPage(): void {
    this.currentPage++;
    this.loadItems();
  }

  getPreviousPage(): void {
    this.currentPage--;
    this.loadItems();
  }

  openDialog(): void {
  
  }

  edit(id): void {
    const modalRef = this.modalService.open(ItemEditComponent);
    modalRef.componentInstance.id = id;

    modalRef.result.then((data) => {
    }, (reason) => {
      this.loadItems();
    });
  }

  remove(id): void {
    this.itemService.removeItem(id).subscribe(data => {
      this.loadItems();
      this.toastr.success('Item successfuly removed!', 'Success');
    },
    err => {
      this.toastr.error('There is a problem with removing item.', 'Error')
    });
  }

  isAdmin() {
    let authority = this.userService.getRole();
      return authority === 'ROLE_ADMIN';
  }
}
