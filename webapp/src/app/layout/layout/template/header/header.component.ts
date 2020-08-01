import { Component, OnInit } from '@angular/core';
import { AppSettings } from '../../../../app.settings';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  appName: string = AppSettings.APP_NAME;

  constructor() { }

  ngOnInit(): void {
  }

}
