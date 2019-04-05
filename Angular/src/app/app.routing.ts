import { RouterModule } from '@angular/router';

/*import { BookListComponent } from './book-list.component';
import { BookDetailComponent } from './book-detail.component';
import { BookFormComponent } from './book-form.component';*/

import { BookFormComponent } from './book/bookForm.component';
import { BookDetailComponent } from './book/bookDetail.component';
import { AuthorFormComponent } from './author/authorForm.component';
import { AuthorDetailComponent } from './author/authorDetail.component';
import { AppComponent } from './app.component';
import { ThemeFormComponent } from './theme/themeForm.component';
import { ThemeDetailComponent } from './theme/themeDetail.component';
import { AuthorComponent } from './author/author.component';
import { IndexComponent } from './index/index.component';

const appRoutes = [
  /*{ path: 'books', component: BookListComponent, useAsDefault: true },
  { path: 'book/new', component: BookFormComponent },
  { path: 'book/:id', component: BookDetailComponent },*/
  {path: '', component:IndexComponent},
  { path: 'book', component: BookFormComponent},
  { path: 'author', component: AuthorFormComponent },
  { path: 'theme', component: ThemeFormComponent },
  { path: 'author/:id', component: AuthorComponent },
  { path: 'book/:id', component: BookDetailComponent },
  { path: 'theme/:id', component: ThemeDetailComponent },
];

export const routing = RouterModule.forRoot(appRoutes);
