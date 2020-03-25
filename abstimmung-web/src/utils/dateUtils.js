/*
function ddmm(input) {
    const mm = input.getMonth() + 1; // getMonth() is zero-based
    const dd = this.getDate();

  return [
    (mm>9 ? '' : '0') + mm,
    (dd>9 ? '' : '0') + dd].join('');
}
*/

export function dm(input) {
    const m = input.getMonth() + 1; // getMonth() is zero-based
    const d = input.getDate();
    return d + '.' + m + '.';
}