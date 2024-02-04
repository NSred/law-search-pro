import {
  ChangeDetectionStrategy,
  Component,
  EventEmitter,
  forwardRef,
  Input,
  OnInit,
  Output,
  ViewEncapsulation,
} from '@angular/core';
import {ControlValueAccessor, FormsModule, NG_VALUE_ACCESSOR} from '@angular/forms';
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-checkbox-base',
  standalone: true,
  templateUrl: './checkbox-base.component.html',
  styleUrl: './checkbox-base.component.scss',
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => CheckboxBaseComponent),
      multi: true,
    },
  ],
  encapsulation: ViewEncapsulation.None,
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [
    FormsModule,
    NgIf
  ]
})
export class CheckboxBaseComponent implements ControlValueAccessor, OnInit {
  @Input() noLabel: boolean = false;
  @Input() isInverted: boolean = false;
  @Input() label: string = '';
  @Input() disabled: boolean = false;
  @Input() initialState = true;
  private _value: boolean = this.initialState;
  @Output() valueChange: EventEmitter<boolean> = new EventEmitter<boolean>();
  @Output() onClick: EventEmitter<void> = new EventEmitter<void>();
  private onChange: (value: any) => void = () => {};
  private onTouched: () => void = () => {};
  @Input() indeterminate = false;
  @Output() onCheckboxChange: EventEmitter<boolean> =
    new EventEmitter<boolean>();

  ngOnInit() {}

  @Input()
  get value() {
    return this._value;
  }

  set value(newValue: boolean) {
    if (newValue !== this._value) {
      this._value = newValue;
      this.onChange(newValue);
      this.valueChange.emit(newValue);
    }
  }

  writeValue(value: boolean): void {
    this._value = value;
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  setDisabledState(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }

  click() {
    this.onClick.emit();
  }

  changeCheckbox() {
    this.onCheckboxChange.emit(this.value);
  }
}
