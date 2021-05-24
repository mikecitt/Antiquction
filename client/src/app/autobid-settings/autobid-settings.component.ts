import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { UserService } from '../service';

@Component({
  selector: 'app-autobid-settings',
  templateUrl: './autobid-settings.component.html',
  styleUrls: ['./autobid-settings.component.css']
})
export class AutobidSettingsComponent implements OnInit {

  close = false;

  form = this.fb.group({
    maxAutoBid: [''],
    notificationAutoBid: ['']
  });

  constructor(
    private modal: NgbActiveModal,
    private userService: UserService,
    public toastr: ToastrService,
    private fb: FormBuilder
  ) { }

  ngOnInit(): void {
    this.userService.getSettings().subscribe(result => {
  		this.form.patchValue(result);
  	})
  }

  closeDialog() {
  	this.close = true;
  	this.modal.dismiss('cancel click');
  }

  update() {
    let payload = this.form.getRawValue();

  	this.userService.updateSettings(payload).subscribe(result => {
      this.toastr.success('Settings successfuly updated!', 'Success');
  		this.modal.dismiss('cancel click')
  	}, err => {
      this.modal.dismiss('cancel click')
      this.toastr.error('There is a problem with updating settings.','Error')
    })

  }

}
