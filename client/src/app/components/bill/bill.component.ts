import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ItemService } from 'src/app/service';

class Bill {
  itemName: string;
  userUsername: string;
  price: string;
}

@Component({
  selector: 'app-bill',
  templateUrl: './bill.component.html',
  styleUrls: ['./bill.component.css']
})
export class BillComponent implements OnInit {

  @Input()
  public id;

  bill: Bill;

  close = false;

  constructor(
    private modal: NgbActiveModal,
    private itemService: ItemService
  ) { }

  ngOnInit(): void {
    this.itemService.getBill(this.id).subscribe(result => {
      this.bill = new Bill();
      this.bill.itemName = result['itemName'];
      this.bill.userUsername = result['userUsername'];
      this.bill.price = result['price'];
    })
  }

  closeDialog() {
  	this.close = true;
  	this.modal.dismiss('cancel click');
  }

}
