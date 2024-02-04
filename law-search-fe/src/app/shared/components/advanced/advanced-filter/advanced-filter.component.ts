import {Component, EventEmitter, Input, Output} from '@angular/core';
import {DropdownOption, SelectBaseComponent} from "../../selects/select-base/select-base.component";
import {InputFieldComponent} from "../../inputs/input-field/input-field.component";
import {CheckboxBaseComponent} from "../../checkboxes/checkbox-base/checkbox-base.component";
import {FormControl, Validators} from "@angular/forms";

export interface CurrentValues {
  currentField: string,
  currentValue: string,
  isPhrase: boolean,
  isNotOperator: boolean
}

@Component({
  selector: 'app-advanced-filter',
  standalone: true,
  imports: [
    SelectBaseComponent,
    InputFieldComponent,
    CheckboxBaseComponent
  ],
  templateUrl: './advanced-filter.component.html',
  styleUrl: './advanced-filter.component.scss'
})
export class AdvancedFilterComponent {
  @Input() index: number = 0;
  @Output() onAnd: EventEmitter<void> = new EventEmitter<void>();
  @Output() onOr: EventEmitter<void> = new EventEmitter<void>();
  inputValueForm = new FormControl('', [Validators.required]);
  fieldOptions: DropdownOption<string>[] = [
    { text: 'governmentName', value: 'governmentName' },
    { text: 'governmentType', value: 'governmentType' },
    { text: 'signatoryPersonName', value: 'signatoryPersonName' },
    { text: 'signatoryPersonSurname', value: 'signatoryPersonSurname' },
    { text: 'content', value: 'content' },
  ];
  currentField: DropdownOption<string> = {
    text: 'governmentName',
    value: 'governmentName',
  };
  isPhrase: boolean = false;
  isNotOperator: boolean = false;

  getCurrentValues() {
    let currentValues: CurrentValues = {
      currentField: this.currentField.value ?? '',
      currentValue: this.inputValueForm.value ?? '',
      isPhrase: this.isPhrase,
      isNotOperator: this.isNotOperator
    }
    return currentValues;
  }

  onSelectionChange($event: string | null) {
    if ($event != null) {
      this.currentField.text = $event;
    }
    this.currentField.value = $event;
  }

  phraseChange() {
    this.isPhrase = !this.isPhrase;
  }

  operatorChange() {
    this.isNotOperator = !this.isNotOperator;
  }

  onAndClick() {
    this.onAnd.emit();
  }

  onOrClick() {
    this.onOr.emit();
  }
}
