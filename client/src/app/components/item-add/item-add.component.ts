import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { ItemService } from '../../service';

@Component({
  selector: 'app-item-add',
  templateUrl: './item-add.component.html',
  styleUrls: ['./item-add.component.css']
})
export class ItemAddComponent implements OnInit {

  form = this.fb.group({
    name: ['', Validators.compose([Validators.required, Validators.minLength(5)])],
    description: ['', Validators.compose([Validators.required])],
    startPrice: ['', Validators.compose([Validators.required])],
    dateEnd: ['', Validators.compose([Validators.required])]
  });

  close = false;

  constructor(
    public modal: NgbActiveModal,
    private fb: FormBuilder,
    private service: ItemService,
    public toastr: ToastrService
  ) { }

  ngOnInit(): void {
  }

  closeDialog() {
  	this.close = true;
  	this.modal.dismiss('cancel click');
  }

  addItem() {
    let payload = this.form.getRawValue();

    this.service.addItem(payload).subscribe(data => {
      this.toastr.success('Item successfuly added!', 'Success');
      this.close = true;
      this.modal.dismiss('cancel click');
    }, err => {
      this.toastr.error('Problem with adding an item.', 'Error');
    });
  }

}
