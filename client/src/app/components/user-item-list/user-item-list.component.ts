import { Component, OnInit } from '@angular/core';
import { ItemService, UserService } from 'src/app/service';

interface Item {
  id: string;
  name: string;
  dateEnd: string;
  startPrice: string;
  bids: any;
}

@Component({
  selector: 'app-user-item-list',
  templateUrl: './user-item-list.component.html',
  styleUrls: ['./user-item-list.component.css']
})
export class UserItemListComponent implements OnInit {

  items: Item[];
  todayDate: Date;

  constructor(
    private itemService: ItemService,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.loadItems();
  }

  loadItems(): void {
    this.todayDate = new Date();
    this.itemService.getMyBiddingItems().subscribe(data => {
      this.items = data;
      console.log(this.items);
    })
  }

  checkExpired(dateEndString: string) {
    var dateEnd = new Date(dateEndString);
    return dateEnd > this.todayDate;
  }

  getState(item: Item) {
    if (this.checkExpired(item.dateEnd)) {
      return "In progress";      
    } else {
      if(item.bids[item.bids.length-1].userUsername == this.userService.currentUser.username)
        return "Won";
      else
        return "Lost";
    }
  }

}
