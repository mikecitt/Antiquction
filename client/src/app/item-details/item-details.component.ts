import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { AuthService, ItemService, UserService } from '../service';

class Item {
  name: string;
  description: string;
  dateEnd: string;
  price: string;
  bids: Bid[];
}

class Bid {
  userUsername: string;
  bidPrice: string;
}

@Component({
  selector: 'app-item-details',
  templateUrl: './item-details.component.html',
  styleUrls: ['./item-details.component.css']
})
export class ItemDetailsComponent implements OnInit {
  @Input()
  public id;

  bidAllowed = true;

  item: Item = new Item();

  close = false;

  bidPrice = new FormControl('');

  constructor(
    private itemService: ItemService,
    private modal: NgbActiveModal,
    private fb: FormBuilder,
    private toastr: ToastrService,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.initItem();
  }

  initItem() {
    this.itemService.getItem(this.id).subscribe(result => {
      this.item.name = result['name'];
      this.item.description = result['description'];
      this.item.dateEnd = result['dateEnd'];
      this.item.price = result['price'];
      this.item.bids = result['bids'];
      this.bidPrice.setValue(result['price'] + 1);
      if (this.item.bids[this.item.bids.length - 1].userUsername == this.userService.currentUser.username) {
        this.bidAllowed = false;    
      }
  	})
  }

  onBid() {
    this.itemService.bidItem(this.id, this.bidPrice.value).subscribe(data => {
      this.toastr.success('Bid successfuly added!', 'Success');
      this.initItem();
      this.bidPrice.setValue("");
    }, err => {
      this.toastr.error('Problem with bidding.', 'Error');
    });
  }

  closeDialog() {
  	this.close = true;
  	this.modal.dismiss('cancel click');
  }
}
