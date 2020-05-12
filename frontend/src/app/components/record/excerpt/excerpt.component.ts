import { Component, Input, OnInit } from "@angular/core";

@Component({
  selector: "app-excerpt",
  templateUrl: "./excerpt.component.html",
  styleUrls: ["./excerpt.component.scss"],
})
export class ExcerptComponent implements OnInit {
  constructor() {}

  @Input() excerpt_text: string;
  @Input() can_Edit: boolean;
  @Input() is_Deactivated: boolean;

  ngOnInit(): void {}
}
