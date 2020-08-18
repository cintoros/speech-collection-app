export class LanguageDto {
  id: number;
  languageId: number;
  languageName: string;

  constructor($id: number, $languageId: number, $languageName: string) {
    this.id = $id;
    this.languageId = $languageId;
    this.languageName = $languageName;
  }
}
