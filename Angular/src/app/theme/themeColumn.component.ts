
import { Component, ChangeDetectorRef, AfterViewInit, ViewChild, TemplateRef, OnInit, Input } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { MatIconRegistry, MatDialog } from '@angular/material';
import { TdMediaService, TdDigitsPipe, TdLayoutManageListComponent, tdRotateAnimation } from '@covalent/core';
import {ActivatedRoute,Router} from '@angular/router'
import { Theme, ThemeService } from './theme.service';
import { LoginService } from '../login/login.service';

@Component({
    selector: 'themeColumn',
    templateUrl: './themeColumn.component.html',
    styleUrls: ['../app.component.css'],
    animations: [tdRotateAnimation],
})
export class ThemeColumnComponent implements OnInit{
    @Input()
    themes: Theme[];
    page: number;
    name: string;

    constructor(
        private router: Router,
        activatedRoute: ActivatedRoute,
        private service: ThemeService,
        public loginService: LoginService
    ){
        this.page=0;
    }

    ngOnInit() {
        var createUrl: string;
        createUrl = "?page=" + (this.page);
        console.log(createUrl);
        if(this.themes!=null){}
        else{
            this.service.getThemes(createUrl).subscribe(
            themes => this.themes = themes,
            error => console.log(error)
            );
        }
    }

    searchTheme(name:string){
        console.log("searchpulsado")
        console.log("Theme search name: ", name)
        this.service.searchTheme(name).subscribe(            
            themes => this.themes = themes,
            error => console.log(error) 
        );
        console.log(this.themes);
    }

    deleteTheme(theme:Theme){
        console.log("delete pulsado con id: ", theme.id)
        let aux:Theme[];
        aux = this.themes;
        aux.forEach( (item, index) => {
            if(item === theme) aux.splice(index,1);
          });
        this.service.deleteTheme(theme).subscribe(            
            themes =>  this.themes = aux,
            error => console.log(error) 
        );
        console.log(this.themes);
    }

    loadMore(){
        var createUrl: string;
        this.page +=1;
        createUrl = "?page=" + (this.page);
        this.service.getThemes(createUrl).subscribe(
            themes => this.themes = this.themes.concat(themes),
            error => console.log(error)
         );
         console.log(this.themes);
    }
    
    newTheme() {
        this.router.navigate(['/theme']);
    }


}
