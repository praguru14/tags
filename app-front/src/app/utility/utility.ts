import { ValidatorFn, AbstractControl, ValidationErrors } from "@angular/forms";
import { jwtDecode } from "jwt-decode";

export class Utility {
  static isLoggedIn = false;

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

  static getEmailFromToken(): string | null {
    const jwtToken = localStorage.getItem('accessToken');

    if (jwtToken) {
      try {
        const decodedToken: any = jwtDecode(jwtToken);
        return decodedToken.sub;
      } catch (error) {
        console.error('Error decoding JWT token:', error);
        return null;
      }
    } else {
      console.error('JWT token not found in localStorage.');
      return null;
    }
  }
}
