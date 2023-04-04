---
sidebar_position: 70
title: Numeric Box
---
import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';

<div style={{width: "100%" , display: "flex", justifyContent: "flex-end", marginBottom: "-50px"}}>
<p style={{color: "gray"}} >API:&nbsp;</p>
<b><a href="https://javadoc.io/static/org.dwcj/dwcj-engine/0.15.0/org/dwcj/controls/numericbox/NumericBox.html" style={{justifySelf: "flex-end"}}> Java </a></b>
</div>

### Labeling

Labels can be easily added above the control without the need to create a separate Label component above the control. Use the `setAttribute()` function to create a label. The attribute to be changed is `label`, and the value should be the desired label text.

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.numericboxdemos.NumericboxLabel' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/numericboxdemos/NumericboxLabel.java'
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/code_snippets/numericbox/Label.txt'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/numericboxstyles/label_styles.css' 
javaHighlight='{22}'
height = '150px'
/>

<br/>

### Placeholder

It is also possible to configure text that appears in the input when it has no value set by utilizing the placeholder attribute. Use the `setAttribute()` function to set placeholder text. The attribute to be changed is `placeholder`, and the value should be the desired text.

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.numericboxdemos.NumericboxPlaceholder' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/numericboxdemos/NumericboxPlaceholder.java'
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/code_snippets/numericbox/Placeholder.txt'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/numericboxstyles/placeholder_styles.css' 
javaHighlight='{22}'
height = '150px'
/>

<br />

### Masking

In order to enforce certain ruled on numeric input, masking has been implemented to allow developers to customize input constraints.

<table>
    <thead>
    <tr>
        <th align="center">Mask</th>
        <th align="center">Supported</th>
        <th align="left">Description</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td align="center">0</td>
        <td align="center">✓</td>
        <td align="left">A zero is always replaced by a digit(0..9).</td>
    </tr>
    <tr>
        <td align="center">#</td>
        <td align="center">✓</td>
        <td align="left">The pound sign is used to suppress leading zeroes. It is replaced by the fill character for leading zeroes to the left of the decimal point. For trailing zeros to the right of the decimal point it is replaced by a space or a zero (see <a href="https://basishub.github.io/bbj-masks/docs/api/class/src/NumberMask/NumberMask.js~NumberMask.html" rel="nofollow">forceTrailingZeros option</a>). Any other time it is replaced by a digit.</td>
    </tr>
    <tr>
        <td align="center">,</td>
        <td align="center">✓</td>
        <td align="left">To the left of the decimal point, the comma is replaced by the fill character if no digits have yet been placed. Any other time, it results in a comma.</td>
    </tr>
    <tr>
        <td align="center">*</td>
        <td align="center">✓</td>
        <td align="left">The asterisk "*" is inserted into the number.</td>
    </tr>
    <tr>
        <td align="center">.</td>
        <td align="center">✓</td>
        <td align="left">The decimal point is replaced by a decimal point if any digits appear in the output mask. Otherwise, it is replaced by the fill character. After the decimal point, the fill character becomes a space.</td>
    </tr>
    <tr>
        <td align="center">$</td>
        <td align="center">✓</td>
        <td align="left">The dollar sign always results in a dollar sign.</td>
    </tr>
    <tr>
        <td align="center">(</td>
        <td align="center">✓</td>
        <td align="left">left parenthesis results in a "(" if the number is negative, or the fill character if positive.</td>
    </tr>
    <tr>
        <td align="center">)</td>
        <td align="center">✓</td>
        <td align="left">A right parenthesis results in a ")" if the number is negative, or the fill character if positive.</td>
    </tr>
    <tr>
        <td align="center">)</td>
        <td align="center">✓</td>
        <td align="left">A right parenthesis results in a ")" if the number is negative, or the fill character if positive.</td>
    </tr>
    <tr>
        <td align="center">CR</td>
        <td align="center">✓</td>
        <td align="left">The characters "CR" are inserted into the number if the number is negative. Two spaces are inserted if the number is positive.</td>
    </tr>
    <tr>
        <td align="center">DR</td>
        <td align="center">✓</td>
        <td align="left">The characters "CR" are inserted into the number if the number is negative. The characters "DR" are inserted if the number is positive.</td>
    </tr>
    <tr>
        <td align="center">B</td>
        <td align="center">✓</td>
        <td align="left">The upper case "B" always becomes a space. Any other character is simply copied to the result.positive.</td>
    </tr>
    </tbody>
</table>

<br/>

### Expanse

DWCJ Numeric Box components come with 5 expanses for quick styling without the use of CSS.
Below are the various expanses supported for the numeric box control: <br/>

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.numericboxdemos.NumericboxExpanses' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/numericboxdemos/NumericboxExpanses.java'
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/code_snippets/numericbox/Expanse.txt'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/numericboxstyles/expanse_styles.css' 
javaHighlight='{22,26,30,34,38}'
height = '200px'
/>

<br/>

|Numeric Box Expanses|
|-|
|<ul><li>```NumericBox.Expanse.XSMALL```</li><li>```NumericBox.Expanse.SMALL```</li><li>```NumericBox.Expanse.MEDIUM```</li><li>```NumericBox.Expanse.LARGE```</li><li>```NumericBox.Expanse.XLARGE```</li></ul>|
