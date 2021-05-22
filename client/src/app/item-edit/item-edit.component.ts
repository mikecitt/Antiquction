import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-item-edit',
  templateUrl: './item-edit.component.html',
  styleUrls: ['./item-edit.component.css']
})
export class ItemEditComponent implements OnInit {
  @Input()
  public id;

  close=false;

  constructor() { }

  ngOnInit(): void {
    console.log("passed:" + this.id);
  }

}
