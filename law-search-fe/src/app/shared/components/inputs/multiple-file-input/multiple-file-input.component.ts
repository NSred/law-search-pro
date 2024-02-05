import {Component, EventEmitter, Output} from '@angular/core';

@Component({
  selector: 'app-multiple-file-input',
  standalone: true,
  imports: [],
  templateUrl: './multiple-file-input.component.html',
  styleUrl: './multiple-file-input.component.scss'
})
export class MultipleFileInputComponent {
  @Output() multipleFilesSelected = new EventEmitter<File[]>();

  handleMultipleFilesChange(event: Event): void {
    const element = event.target as HTMLInputElement;
    const files: FileList | null = element.files;

    if (files) {
      const fileList: File[] = Array.from(files);
      this.multipleFilesSelected.emit(fileList);
    }
  }
}
