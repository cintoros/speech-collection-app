export class Text {
  id: number;
  dialectId: number;
  dataElementId: number;
  isSentenceError: boolean;
  text: string;

  constructor(id: number, dialectId: number, dataElementId: number, isSentenceError: boolean, text: string) {
    this.id = id;
    this.dialectId = dialectId;
    this.dataElementId = dataElementId;
    this.isSentenceError = isSentenceError;
    this.text = text;
  }
}
