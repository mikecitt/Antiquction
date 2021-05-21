import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  form: FormGroup;
	errorMessage: string | undefined;

  constructor(
    private formBuilder: FormBuilder,
    private service: AuthService
  ) {
    this.form = this.formBuilder.group({
      username: ['', Validators.compose([Validators.required])],
      password: ['', Validators.compose([Validators.required])]

    })
  }

  ngOnInit(): void {
  }

  onSubmit(): void {
  	console.log(this.form.value)

  	this.service.login(this.form.value)
  	.subscribe(data => {

        },
        error => {
          this.errorMessage = 'Wrong username or password.';
        });
  }
}
