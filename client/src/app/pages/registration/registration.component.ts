import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService, UserService } from 'src/app/service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {
  form: FormGroup;

	success: string;
	fail: string;

  errorRePassword: boolean;

  constructor(
    private authService: AuthService,
    private formBuilder: FormBuilder
  ) {
    this.form = this.formBuilder.group({
      username: ['', Validators.compose([Validators.required, Validators.minLength(5), Validators.maxLength(15)])],
      password: ['', Validators.compose([Validators.required])],
      rePassword: ['', Validators.compose([Validators.required])],
    });
  }

  ngOnInit(): void {
  }

  register() {
    if (this.form.controls['rePassword'].value !== this.form.controls['password'].value) {
      this.errorRePassword = true;
      return;
    }

    this.success = null;
    this.fail = null;

    let formObj = this.form.getRawValue();
    delete formObj['rePassword'];

    this.authService.register(formObj).subscribe(data => {
      this.success = 'Registration successful.';
    },
    (err) => { this.fail = 'User already exist.' })
  }

  focusField() {
    this.errorRePassword = false;
  }

}
