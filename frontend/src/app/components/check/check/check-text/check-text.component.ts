import { Component, OnInit, Input } from "@angular/core";

@Component({
  selector: "app-check-text",
  templateUrl: "./check-text.component.html",
  styleUrls: ["./check-text.component.scss"],
})
export class CheckTextComponent implements OnInit {
  constructor() {}

  @Input() excerpt_text: string;

  ngOnInit(): void {}
}
