import { ValidatorFn, AbstractControl, ValidationErrors } from "@angular/forms";

export class Utility{
    
    static bloodGroupValidator(): ValidatorFn {
        return (control: AbstractControl): ValidationErrors | null => {
            const value = control.value;
            const bloodGroupPattern = /^(A\+|A\-|B\+|B\-|O\+|O\-|AB\+|AB\-|N\/A)$/;

            if (!bloodGroupPattern.test(value)) {
                return { invalidBloodGroup: true };
            }

            return null; 
        };
    }
}