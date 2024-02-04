import {Component, EventEmitter, forwardRef, Input, OnInit, Output} from '@angular/core';
import {FormsModule, NG_VALUE_ACCESSOR} from "@angular/forms";
import {NgForOf} from "@angular/common";

export type DropdownOption<T> = {
  text: string;
  value: T | null;
};

@Component({
  selector: 'app-select-base',
  standalone: true,
  templateUrl: './select-base.component.html',
  styleUrl: './select-base.component.scss',
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => SelectBaseComponent),
      multi: true,
    },
  ],
  imports: [
    FormsModule,
    NgForOf
  ]
})
export class SelectBaseComponent<T> implements OnInit {
  @Input()
  currentOption: DropdownOption<T> = {text: '', value: null};
  @Input()
  options: DropdownOption<T>[] = [];
  @Output() selectedOption: EventEmitter<T | null> = new EventEmitter();
  ngOnInit(): void {
    this.emitSelect();
  }

  private emitSelect() {
    this.selectedOption.emit(this.currentOption.value);
  }
  onSelectChange(event: T) {
    this.selectedOption.emit(event);
  }
}
