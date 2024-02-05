import {Component, inject} from '@angular/core';
import {AdvancedFilterComponent} from "../../shared/components/advanced/advanced-filter/advanced-filter.component";
import {AsyncPipe, NgForOf, NgIf} from "@angular/common";
import {ButtonComponent} from "../../shared/components/button/button.component";
import {ContractCardComponent} from "../../shared/components/contract-card/contract-card.component";
import {InputFieldComponent} from "../../shared/components/inputs/input-field/input-field.component";
import {FileInputComponent} from "../../shared/components/inputs/file-input/file-input.component";
import {
  MultipleFileInputComponent
} from "../../shared/components/inputs/multiple-file-input/multiple-file-input.component";
import {DocumentService} from "../../shared/services/document.service";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-upload-documents',
  standalone: true,
  imports: [
    AdvancedFilterComponent,
    AsyncPipe,
    ButtonComponent,
    ContractCardComponent,
    NgForOf,
    NgIf,
    InputFieldComponent,
    FileInputComponent,
    MultipleFileInputComponent
  ],
  templateUrl: './upload-documents.component.html',
  styleUrl: './upload-documents.component.scss'
})
export class UploadDocumentsComponent {
  toastr = inject(ToastrService)
  documentService = inject(DocumentService)
  contract: File | null = null;
  laws: File[] = [];
  upload() {
    const formData = new FormData();

    if (this.contract) {
      formData.append('contract', this.contract, this.contract.name);
    }

    this.laws.forEach((file, index) => {
      formData.append(`laws[${index}]`, file, file.name);
    });

    this.documentService.upload(formData).subscribe({
      next: _ => {
        this.toastr.success('Success', 'Successfully uploaded documents')
      },
      error: err => {
        this.toastr.error('Error', err.message)
      }
    })
  }

  contractSelected(file: File) {
    this.contract = file;
  }

  lawsSelected(files: File[]) {
    this.laws = files;
  }
}
