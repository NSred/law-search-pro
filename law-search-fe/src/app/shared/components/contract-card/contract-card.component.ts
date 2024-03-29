import {Component, EventEmitter, Input, Output} from '@angular/core';
import {ButtonComponent} from "../button/button.component";
import {Contract} from "../../services/search.service";

@Component({
  selector: 'app-contract-card',
  standalone: true,
  imports: [
    ButtonComponent
  ],
  templateUrl: './contract-card.component.html',
  styleUrl: './contract-card.component.scss'
})
export class ContractCardComponent {
  @Input() contract: Contract = {contractId: '', fileName: '',
    governmentName: '', governmentType: '',
    signatoryPersonName: '', signatoryPersonSurname: '', highlight: ''}
  @Output() onDownload: EventEmitter<string> = new EventEmitter

  download() {
    this.onDownload.emit(this.contract.fileName);
  }
}
