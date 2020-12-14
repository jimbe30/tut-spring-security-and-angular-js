import { Component } from '@angular/core';
import { AppService } from './app.service';
import { HttpClient } from '@angular/common/http';

@Component({
	templateUrl: './home.component.html'
})
export class HomeComponent {

	title = 'Demo';
	greeting = {};

	constructor(private app: AppService, private http: HttpClient) {
		http.get('/resource').subscribe(data => this.greeting = data);
	}

	authenticated() {
		return this.app.authenticated;
	}

	public setHttp(value: HttpClient): void {
		this.http = value;
	}
	public getHttp(): HttpClient {
		return this.http;
	}

}
