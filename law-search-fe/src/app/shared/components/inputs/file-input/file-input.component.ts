import {Component, EventEmitter, Output} from '@angular/core';

@Component({
  selector: 'app-file-input',
  standalone: true,
  imports: [],
  templateUrl: './file-input.component.html',
  styleUrl: './file-input.component.scss'
})
export class FileInputComponent {
  @Output() fileSelected = new EventEmitter<File>();

  handleFileChange(event: Event): void {
    const element = event.target as HTMLInputElement;
    const files = element.files;
    if (files && files.length > 0) {
      const file = files[0];
      this.fileSelected.emit(file); // Emit the selected file
    }
  }
}
