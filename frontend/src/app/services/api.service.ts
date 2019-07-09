import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {TextAudioIndex} from '../models/textAudioIndex';
import {Transcript} from '../models/transcript';

@Injectable({
  providedIn: 'root'
})

export class ApiService {

  constructor(
    private http: HttpClient
  ) {
  }

  url = 'http://localhost:8080/api/match/';

  getTextAudioIndex(id: number): Observable<TextAudioIndex> {
    return this.http.get<TextAudioIndex>(this.url + 'getTextAudioIndex?id=' + id);
  }

  getTextAudioIndexes(): Observable<Array<TextAudioIndex>> {
    return this.http.get<Array<TextAudioIndex>>(this.url + 'getTextAudioIndexes');
  }

  updateTextAudioIndex(textAudioIndex: TextAudioIndex): Observable<any> {
    return this.http.post(this.url + 'updateTextAudioIndex', textAudioIndex);
  }

  getTranscripts(): Observable<Array<Transcript>> {
    return this.http.get<Array<Transcript>>(this.url + 'getTranscripts');
  }

  getTranscript(id: number): Observable<Transcript> {
    return this.http.get<Transcript>(this.url + 'getTranscript?id=' + id);
  }
}