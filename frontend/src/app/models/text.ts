//TODO rename to text -> and refactor
export interface Text {
  excerpt: string;
  id: number;
  //TODO probably add a dto that contains both the text object and the element data
  isSkipped: number;
  isPrivate: boolean;
  isSentenceError: boolean;
  originalTextId: number;
}
