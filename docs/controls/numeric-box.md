---
sidebar_position: 70
---

# NumericBox

### At a Glance

|Parent Class| Interfaces |
|------------|------------|
|[AbstractDwcControl](#)| <ul><li>[HasReadOnly](#)</li><li>[HasTextHighlight](#)</li><li>[HasFocus](#)</li><li>[HasTabTraversal](#)</li><li>[HasTextAlignment](#)</li></ul>|

| Methods |
|------------|
| <ul><li>[`String getCommaCharacter()`](#)</li><li>[`String getDotCharacter()`](#)</li><li>[`String getEditString()`](#)</li><li>[`Integer getError()`](#)</li><li>[`int getLength()`](#)</li><li>[`Integer getMargin()`](#)</li><li>[`String getMask()`](#)</li><li>[`String getRestore()`](#)</li><li>[`BigDecimal getValue()`](#)</li><li>[`Boolean isHighlight()`](#)</li><li>[`Boolean isInsertMode()`](#)</li><li>[`Boolean isNegatable()`](#)</li><li>[`Boolean isPassEnter()`](#)</li><li>[`Boolean isPassTab()`](#)</li><li>[`Boolean isUseEditCommas()`](#)</li><li>[`void restore()`](#)</li><li>[`NumericBox selectAll()`](#)</li><li>[`NumericBox setCommaCharacter(String comma)`](#)</li><li>[`NumericBox setDotCharacter(String dot)`](#)</li><li>[`NumericBox setEditString(String edit)`](#)</li><li>[`NumericBox setExpanse(Expanse expanse)`](#)</li><li>[`NumericBox setHighlight(Boolean highlight)`](#)</li><li>[`NumericBox setInsertMode(Boolean insert)`](#)</li><li>[`NumericBox setLength(Integer len)`](#)</li><li>[`NumericBox setMargin(Integer marginWidth)`](#)</li><li>[`NumericBox setMask(String mask)`](#)</li><li>[`NumericBox setNegatable(boolean negatable)`](#)</li><li>[`NumericBox setPassEnter(Boolean pass)`](#)</li><li>[`NumericBox setPassTab(Boolean pass)`](#)</li><li>[`NumericBox setRestore(float restore)`](#)</li><li>[`NumericBox setUseEditCommas(boolean useCommas)`](#)</li><li>[`NumericBox setValue(BigDecimal value)`](#)</li><li>[`NumericBox setValue(float value)`](#)</li><li>[`NumericBox setValue(int value)`](#)</li></ul>|


| Events |
|------------|
| <ul><li>[`NumericBox onEditModify(Consumer<NumericBoxEditModifyEvent> callback`](#)</li></ul> |

### Labeling

Labels can be easily added above the control without the need to create a separate Label component above the control. 

<iframe 
loading="lazy"
src='http://localhost:8888/webapp/dwcj_control_demos?class=org.dwcj.control_demos.numericboxdemos.NumericboxLabel' 
style={{"width": "100%", "height":"170px"}}></iframe><br/><br />

Use the `setAttribute()` function to create a label. The attribute to be changed is `label`, and the value should be the desired label text.  <br/><br />

```java
import org.dwcj.controls.numericbox.NumericBox;

NumericBox exampleNumericBox = new NumericBox();      //Creates a new NumericBox
exampleNumericBox.setAttribute("label", "Example numeric box label!");     //Gives the numeric box a label with the provided text
```

### Placeholder

It is also possible to configure text that appears in the input when it has no value set by utilizing the placeholder attribute.

<iframe 
loading="lazy"
src='http://localhost:8888/webapp/dwcj_control_demos?class=org.dwcj.control_demos.numericboxdemos.NumericboxPlaceholder' 
style={{"width": "100%", "height":"170px"}}></iframe><br/><br />

Use the `setAttribute()` function to set placeholder text. The attribute to be changed is `placeholder`, and the value should be the desired text.  <br/><br />

```java
import org.dwcj.controls.numericbox.NumericBox;

NumericBox exampleNumericBox = new NumericBox();      //Creates a new NumericBox
exampleNumericBox.setAttribute("placeholder", "Example placeholder");     //Gives the numeric box a label with the provided text
```

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

### Expanse

DWCJ Numeric Box components come with 14 themes built in for quick styling without the use of CSS.
Below are the various expanses supported for the numeric box control: <br/>

<iframe 
loading="lazy"
src='http://localhost:8888/webapp/dwcj_control_demos?class=org.dwcj.control_demos.numericboxdemos.NumericboxExpanses' 
style={{"width": "100%", "height":"125px"}}></iframe><br/>

|Numeric Box Expanses|
|-|
|<ul><li>```NumericBox.Expanse.XSMALL```</li><li>```NumericBox.Expanse.SMALL```</li><li>```NumericBox.Expanse.MEDIUM```</li><li>```NumericBox.Expanse.LARGE```</li><li>```NumericBox.Expanse.XLARGE```</li></ul>|

<br/>Expanses are supported by use of a built-in enum class. To apply an expanse, execute code similar to the following: <br/><br />

```java
import org.dwcj.controls.numericbox.NumericBox;

NumericBox exampleNumericBox = new NumericBox();      //Creates a new NumericBox
exampleNumericBox.setExpanse(NumericBox.Expanse.MEDIUM);     //Sets the numeric box's expanse to the medium size.
```
