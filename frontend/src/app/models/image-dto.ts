export class ImageDto {
  id: number;
  dataElementId: number;
  path: string;
  licence: string;

  constructor($id: number, $dataElementId: number, $path: string, $licence: string) {
    this.id = $id;
    this.dataElementId = $dataElementId;
    this.path = $path;
    this.licence = $licence;
  }
}
