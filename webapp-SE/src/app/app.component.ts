import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  public searchForm: any;
  private searchTypeLucene: boolean = true;
  public loading: boolean = false;
  public flag: boolean = true;

  constructor(
    private formBuilder: FormBuilder
  ){}

  ngOnInit(): void {
    //throw new Error('Method not implemented.');
    this.searchForm = this.formBuilder.group({
      searchInput: ['', Validators.required],
      searchType: ['lucene', Validators.required]
    });
  }
  title = 'News Article Search Engine';

  changeSearchType(event: any) {
    this.searchTypeLucene = event.target.value === 'lucene' ? true : false;
    if (this.searchTypeLucene){
      console.log(`Search type Lucene is selected`);
    } else {
      console.log(`Search type Hadoop is selected`);
    }
  }

  onSubmit() {
    if (this.searchForm.invalid) {
      return;
    }
    this.loading = true;
    this.loading = false;
    this.flag = false;
  }

  get searchInput() {
    return this.searchForm.get('searchInput');
  }

  get searchType() {
    return this.searchForm.get('searchType');
  }
}
