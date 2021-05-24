import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { ItemService } from '../../service';

@Component({
  selector: 'app-item-edit',
  templateUrl: './item-edit.component.html',
  styleUrls: ['./item-edit.component.css']
})
export class ItemEditComponent implements OnInit {
  @Input()
  public id;

  editForm = this.fb.group({
    name: ['', Validators.compose([Validators.required, Validators.minLength(5)])],
    description: ['', Validators.compose([Validators.required])]
  });

  close=false;

  constructor(
    private fb: FormBuilder,
    private itemService: ItemService,
    public modal: NgbActiveModal,
    public toastr: ToastrService
  ) { }

  ngOnInit(): void {
    this.itemService.getItem(this.id).subscribe(result => {
  		this.editForm.patchValue(result);
  	})
  }

  closeDialog() {
  	this.close = true;
  	this.modal.dismiss('cancel click');
  }

  updateItem() {
    let payload = this.editForm.getRawValue();

  	this.itemService.updateItem(this.id, payload).subscribe(result => {
      this.toastr.success('Item successfuly updated!', 'Success');
  		this.modal.dismiss('cancel click')
  	}, err => {
      this.modal.dismiss('cancel click')
      this.toastr.error('There is a problem with updating item.','Error')
    })

  }

}
