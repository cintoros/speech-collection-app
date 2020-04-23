// TODO rename refactor so it is possible to display audio,image,text element
export interface Text {
  id: number;
  isSkipped: number;
  isPrivate: boolean;
  isSentenceError: boolean;
  originalTextId: number;
  text: string;
}
