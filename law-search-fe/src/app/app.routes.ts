import { Routes } from '@angular/router';
import {ContractSearchComponent} from "./pages/contract-search/contract-search.component";
import {UploadDocumentsComponent} from "./pages/upload-documents/upload-documents.component";
import {LocationSearchComponent} from "./pages/location-search/location-search.component";
import {LawSearchComponent} from "./pages/law-search/law-search.component";

export const routes: Routes = [
  {
    path: '',
    component: ContractSearchComponent
  },
  {
    path: 'upload',
    component: UploadDocumentsComponent
  },
  {
    path: 'location',
    component: LocationSearchComponent
  },
  {
    path: 'laws',
    component: LawSearchComponent
  },
];
