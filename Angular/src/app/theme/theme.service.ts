



import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs';
import { map, catchError } from 'rxjs/operators'
import { Book } from '../book/book.service';

export interface Theme {
  id?: number;
  name: string;
  books:Book[];
}

const URL = '/api/themes';

@Injectable()
export class ThemeService {
  constructor(private http: Http) { }

  getThemes(customURL: string) {
    return this.http.get(URL + customURL, { withCredentials: false })
      .pipe(
        map(response => response.json()),
        catchError(error => this.handleError(error))
    );
  }

  getTheme(id: number | string) {
    return this.http.get(URL + "/" +id, { withCredentials: true })
      .pipe(
          map(response => response.json()),
          catchError(error => this.handleError(error))
      );
  }

  deleteTheme(theme: Theme) {
    const headers = new Headers({
      'X-Requested-With': 'XMLHttpRequest'
    });
    const options = new RequestOptions({ withCredentials: true, headers });

    return this.http.delete(URL + "/" + theme.id, options)
      .pipe(
        map(response => undefined),
        catchError(error => this.handleError(error))
      );
  }

  saveTheme (theme:Theme){
    const body= JSON.stringify(theme)
    const headers = new Headers({'Content-Type': 'application/json',withCredentials: true});

    if(!theme.id){
      return this.http.post(URL + "/" ,body, {headers})
      .pipe(
          map(response => response.json()),
          catchError(error => this.handleError(error))
      );
    }
    else{
      return this.http.patch(URL + "/" +theme.id,body, {headers})
      .pipe(
          map(response => response.json()),
          catchError(error => this.handleError(error))
      );
    }  
  }

  searchTheme(name:string){
    return this.http.get(URL + "?name=" + name, { withCredentials: true })
      .pipe(
          map(response => response.json()),
          catchError(error => this.handleError(error))
      );
  }

  private handleError(error: any) {
    console.error(error);
    return Observable.throw('Server error theme (' + error.status + '): ' + error.text());
  }
}



