export class CheckedDataTuple {
  id: number;
  user_id: number;
  data_tuple_id: number;
  type: CheckedDataTupleType;

  constructor(
      $id: number, $user_id: number, $data_tuple_id: number,
      $type: CheckedDataTupleType) {
    this.id = $id;
    this.user_id = $user_id;
    this.data_tuple_id = $data_tuple_id;
    this.type = $type;
  }
}

export enum CheckedDataTupleType {
  SKIPPED = 'SKIPPED',
  CORRECT = 'CORRECT',
  WRONG = 'WRONG'
}
