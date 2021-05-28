import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { ItemAddComponent } from '../item-add/item-add.component';
import { ItemDetailsComponent } from '../item-details/item-details.component';
import { ItemEditComponent } from '../item-edit/item-edit.component';
import { ItemService, UserService } from '../../service';

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
  startPrice: string;
}

@Component({
  selector: 'app-item-list',
  templateUrl: './item-list.component.html',
  styleUrls: ['./item-list.component.css']
})
export class ItemListComponent implements OnInit {
  itemPage: ItemPage | any;
  public currentPage: number;

  todayDate: Date;

  filter = new FormControl('');
  sortBy = new FormControl('name ASC');

  constructor(
      private itemService: ItemService, 
      private userService: UserService, 
      public toastr: ToastrService,
      private modalService: NgbModal
    ) {
    this.currentPage = 0;

      this.filter.valueChanges.subscribe(val => {
        this.itemService.getItems(val, this.currentPage,  this.sortBy.value.split(" ")[0], this.sortBy.value.split(" ")[1]).subscribe(data => {
          this.itemPage = data;        
        })
      });

      this.sortBy.valueChanges.subscribe(val => {
        this.itemService.getItems(this.filter.value, this.currentPage,  val.split(" ")[0], val.split(" ")[1]).subscribe(data => {
          this.itemPage = data;        
        })
      });
  }

  ngOnInit(): void {
    this.todayDate = new Date();
    this.loadItems();
  }

  loadItems(): void {
    this.itemService.getItems(this.filter.value, this.currentPage,  this.sortBy.value.split(" ")[0], this.sortBy.value.split(" ")[1]).subscribe(data => {
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

  edit(id): void {
    const modalRef = this.modalService.open(ItemEditComponent);
    modalRef.componentInstance.id = id;

    modalRef.result.then((data) => {
    }, (reason) => {
      this.loadItems();
    });
  }

  add(): void {
    const modalRef = this.modalService.open(ItemAddComponent);

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

  bid(id) {
    const modalRef = this.modalService.open(ItemDetailsComponent, { size: 'lg' });
    modalRef.componentInstance.id = id;
    modalRef.result.then((data) => {
    }, (reason) => {
      this.loadItems();
    });
  }

  isAdmin() {
    let authority = this.userService.getRole();
      return authority === 'ROLE_ADMIN';
  }

  checkExpired(dateEndString: string) {
    var dateEnd = new Date(dateEndString);
    return dateEnd > this.todayDate;
  }
}
