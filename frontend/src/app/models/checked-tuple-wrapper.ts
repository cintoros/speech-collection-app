import {CheckedDataTuple} from './checked-data-tuple';
import {TupleDto} from './tuple-dto';

export class CheckedTupleWrapper {
  tuple: TupleDto;
  checkedDataTuple: CheckedDataTuple;


  constructor($tuple: TupleDto, $checkedDataTuple: CheckedDataTuple) {
    this.tuple = $tuple;
    this.checkedDataTuple = $checkedDataTuple;
  }
}
