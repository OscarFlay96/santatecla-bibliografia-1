import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs';
import { map, catchError } from 'rxjs/operators'


export interface Author {
  id?: number;
  name: string;
  urlImage?: string;
  birthDate?: Date;
  deathDate?: Date;
  bornPlace?: string;
  urlMap?: string;
  imgId?: number;
}

const URL = '/api/authors';


@Injectable()
export class AuthorService {
    constructor(private http: Http) {}

    getAuthors(customURL: string) {
        return this.http.get(URL+customURL,{ withCredentials: false })
          .pipe(
            map(response => response.json()),
            catchError(error => this.handleError(error))
        );
    }

    private handleError(error: any) {
        console.error(error);
        return Observable.throw('Server error (' + error.status + '): ' + error.text());
    }
}
