import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { ItemService, UserService } from '../../service';

class Item {
  name: string;
  description: string;
  dateEnd: string;
  price: string;
  startPrice: string;
  autoBid: string;
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
  autoBidAllowed = true;

  item: Item;

  close = false;

  bidPrice = new FormControl('');
  autoBidPrice = new FormControl('');

  constructor(
    private itemService: ItemService,
    private modal: NgbActiveModal,
    private fb: FormBuilder,
    private toastr: ToastrService,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.item = new Item();
    this.initItem();
  }

  initItem() {
    this.itemService.getItem(this.id).subscribe(result => {
      this.item.name = result['name'];
      this.item.description = result['description'];
      this.item.dateEnd = result['dateEnd'];
      this.item.price = result['price'];
      this.item.autoBid = result['autoBid'];
      this.item.bids = result['bids'];
      this.item.startPrice = result['startPrice'];
      this.bidPrice.setValue(result['price'] + 1);
      this.autoBidPrice.setValue(result['autoBid']);
      if(this.item.bids.length > 0)
        if (this.item.bids[this.item.bids.length - 1].userUsername == this.userService.currentUser.username) {
          this.bidAllowed = false;    
        }
      var diff = Math.floor(+new Date(result['dateEnd']) - +new Date());
      if(diff < 0) {
        this.bidAllowed = false;
        this.autoBidAllowed = false;
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

  onAutoBid() {
    this.itemService.autoBidItem(this.id, this.autoBidPrice.value).subscribe(data => {
      this.toastr.success('AutoBid successfuly added!', 'Success');
      this.initItem();
      this.autoBidPrice.setValue("");
    }, err => {
      this.toastr.error('Problem with autobidding.', 'Error');
    });
  }

  closeDialog() {
  	this.close = true;
  	this.modal.dismiss('cancel click');
  }

  isAdmin() {
    let authority = this.userService.getRole();
    return authority === 'ROLE_ADMIN';
  }
}
