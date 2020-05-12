export class TextDto {
  id: number;
  isPrivate: boolean;
  text: string;

  constructor(id: number, isPrivate: boolean, text: string) {
    this.id = id;
    this.isPrivate = isPrivate;
    this.text = text;
  }
}
