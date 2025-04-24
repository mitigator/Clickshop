import { DragDirective } from './drag.directive';
import { DomSanitizer } from '@angular/platform-browser';

describe('DragDirective', () => {
  let sanitizer: DomSanitizer;

  beforeEach(() => {
    sanitizer = {} as DomSanitizer; 
  });

  it('should create an instance', () => {
    const directive = new DragDirective(sanitizer); 
    expect(directive).toBeTruthy();
  });
});
